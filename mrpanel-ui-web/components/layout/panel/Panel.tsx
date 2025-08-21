import React, { CSSProperties, forwardRef } from "react";

import styles from './Panel.module.scss';
import classes from "@/util/classes";

export type PanelProps = React.PropsWithChildren<{
    className?: string;
    style?: CSSProperties;
    shadow?: boolean;
}>;

const Panel = forwardRef<HTMLDivElement, PanelProps>(({children, className, style, shadow}, ref) => {
    return <div
      ref={ref}
      className={classes(styles.panel, className, { [styles.shadow]: !!shadow })}
      style={style}
    >
      {children}
    </div>
});
Panel.displayName = "Panel";

export default Panel;