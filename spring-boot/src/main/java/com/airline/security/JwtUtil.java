package com.airline.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

	private final SecretKey secretKey;

	private static final String SECRET = "PRjJmFiz+vZ/hTCAl4UUIADLuCK0f5Bt4sl6+z7t8oU=";

	private static final long ACCESS_EXPIRATION_MS = 60 * 60 * 1000L;
	private static final long REFRESH_EXPIRATION_MS = 7 * 24 * 60 * 60 * 1000L;

	public JwtUtil() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		this.secretKey = Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * Tạo Access Token có chứa userId, email, role, exp 1 giờ.
	 */
	public String generateAccessToken(Long userId, String email, String role) {
		long now = System.currentTimeMillis();
		return Jwts.builder().setSubject(String.valueOf(userId)).claim("email", email).claim("role", role)
				.setIssuedAt(new Date(now)).setExpiration(new Date(now + ACCESS_EXPIRATION_MS))
				.signWith(secretKey, SignatureAlgorithm.HS256).compact();
	}

	/**
	 * Tạo Refresh Token chỉ chứa userId, exp 7 ngày.
	 */
	public String generateRefreshToken(Long userId) {
		long now = System.currentTimeMillis();
		return Jwts.builder().setSubject(String.valueOf(userId)).setIssuedAt(new Date(now))
				.setExpiration(new Date(now + REFRESH_EXPIRATION_MS)).signWith(secretKey, SignatureAlgorithm.HS256)
				.compact();
	}

	/**
	 * Trích xuất claims từ token.
	 * 
	 * @throws ExpiredJwtException nếu token hết hạn.
	 * @throws JwtException        nếu token không hợp lệ.
	 */
	public Claims extractClaims(String token) throws JwtException {
		return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
	}

	/**
	 * Lấy userId (subject) từ token.
	 */
	public Long getUserIdFromToken(String token) {
		return Long.parseLong(extractClaims(token).getSubject());
	}

	/**
	 * Lấy role từ Access Token. Nếu token là Refresh Token thì có thể trả về null.
	 */
	public String getRoleFromToken(String token) {
		return extractClaims(token).get("role", String.class);
	}

	/**
	 * Lấy email từ Access Token. Nếu token là Refresh Token thì có thể trả về null.
	 */
	public String getEmailFromToken(String token) {
		return extractClaims(token).get("email", String.class);
	}

	/**
	 * Kiểm tra token có còn hợp lệ (chữ ký đúng, không hết hạn).
	 */
	public boolean isTokenValid(String token) {
		try {
			extractClaims(token);
			return true;
		} catch (JwtException e) {
			return false;
		}
	}

	/**
	 * Kiểm tra token có hết hạn hay không.
	 */
	public boolean isTokenExpired(String token) {
		try {
			Date expiration = extractClaims(token).getExpiration();
			return expiration.before(new Date());
		} catch (ExpiredJwtException e) {
			return true;
		} catch (JwtException e) {
			// Token không hợp lệ thì cũng xem như expired
			return true;
		}
	}

	/**
	 * Validate token và trả về lỗi chi tiết nếu có. Trả về null nếu hợp lệ.
	 */
	public String validateToken(String token) {
		try {
			extractClaims(token);
			return null; // Token hợp lệ
		} catch (ExpiredJwtException e) {
			return "Token đã hết hạn";
		} catch (UnsupportedJwtException e) {
			return "Token không được hỗ trợ";
		} catch (MalformedJwtException e) {
			return "Token bị sai định dạng";
		} catch (SignatureException e) {
			return "Chữ ký token không hợp lệ";
		} catch (IllegalArgumentException e) {
			return "Token không được để trống";
		}
	}
}
