import React from "react";

import styles from './HoverMenu.module.scss';
import classes from "@/util/classes";

export type HoverMenuAlign = 'left' | 'right';

export type HoverMenuProps = React.PropsWithChildren<{
    trigger: React.ReactNode;
    align?: HoverMenuAlign;
}>;

export default function HoverMenu({trigger, children, align}: HoverMenuProps) {
    align ??= 'left';

    return <div className={styles.menuContainer}>
        {trigger}
        <div className={classes(
            styles.content, {
                [styles.alignLeft]: align === 'left',
                [styles.alignRight]: align === 'right'
            }
        )}>
            {children}
        </div>
    </div>
}

HoverMenu.Item = function DropdownItem({children, onClick}: { children: React.ReactNode, onClick?: () => void }) {
    return <div className={styles.item} onClick={onClick}>{children}</div>;
}