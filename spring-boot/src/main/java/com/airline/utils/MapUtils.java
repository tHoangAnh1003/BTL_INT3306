package com.airline.utils;

import java.util.Map;

public class MapUtils {

    /**
     * Lấy giá trị từ map với key và kiểu dữ liệu chỉ định.
     * Nếu không tìm thấy key hoặc không thể ép kiểu, trả về null.
     *
     * @param map     map nguồn
     * @param key     khóa cần lấy
     * @param clazz   kiểu dữ liệu mong muốn
     * @param <T>     kiểu dữ liệu trả về
     * @return giá trị đã ép kiểu hoặc null nếu không tồn tại hoặc lỗi kiểu
     */
    @SuppressWarnings("unchecked")
    public static <T> T getObject(Map<String, Object> map, String key, Class<T> clazz) {
        if (map == null || key == null || clazz == null) return null;

        Object value = map.get(key);

        if (value == null) return null;

        if (clazz.isInstance(value)) {
            return (T) value;
        }

        try {
            if (clazz == String.class) {
                return (T) value.toString();
            }

            if (clazz == Integer.class) {
                if (value instanceof Number) {
                    return (T) Integer.valueOf(((Number) value).intValue());
                }
                return (T) Integer.valueOf(Integer.parseInt(value.toString()));
            }

            if (clazz == Long.class) {
                if (value instanceof Number) {
                    return (T) Long.valueOf(((Number) value).longValue());
                }
                return (T) Long.valueOf(Long.parseLong(value.toString()));
            }

            if (clazz == Double.class) {
                if (value instanceof Number) {
                    return (T) Double.valueOf(((Number) value).doubleValue());
                }
                return (T) Double.valueOf(Double.parseDouble(value.toString()));
            }

            if (clazz == Boolean.class) {
                if (value instanceof Boolean) {
                    return (T) value;
                }
                return (T) Boolean.valueOf(value.toString());
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
