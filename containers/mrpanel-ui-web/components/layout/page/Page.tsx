import React, {forwardRef} from "react";
import classes from "@/util/classes";
import styles from './Page.module.scss';

export type PageProps = React.PropsWithChildren<{
    className?: string;
    style?: React.CSSProperties;
}>;

const Page = forwardRef<HTMLDivElement, PageProps>(({children, className, style}, ref) => {
    return <div className={classes(styles.container, className)} style={style} ref={ref}>
        <div className={styles.page}>
            {children}
        </div>
    </div>
});
Page.displayName = "Page";

export default Page;