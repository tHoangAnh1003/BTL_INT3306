import { memo } from "react";

const MasterAdLayout = ({ children, ...props }) => {
  return (
    <div {...props}>
      {children}
    </div>
  );
}

export default memo(MasterAdLayout);