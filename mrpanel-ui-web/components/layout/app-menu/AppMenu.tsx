'use client';

import { memo } from "react";
import Link from "next/link";
import styles from './AppMenu.module.scss';
import classes from "@/util/classes";
import { usePathname } from "next/navigation";
import VerticalDivider from "@/components/layout/divider/VerticalDivider";
import { LogOut, UsersRound } from "lucide-react";

interface MenuItem {
    label: string,
    url: string,
    active: boolean
}

const AppMenuItem = memo((props: { item: MenuItem }) => {
    const {active, url, label} = props.item;
    return <Link className={classes(styles.menuItem, {[styles.menuItemActive]: active})} href={url}>{label}</Link>
});
AppMenuItem.displayName = "AppMenuItem";

const AppMenuStateless = memo((props: {
    menuItems: MenuItem[]
}) => {
    return <div className={styles.appMenu}>
        <div>
            MR
        </div>
        <div>
            {props.menuItems.map(item => <AppMenuItem item={item} key={item.url + item.label}/>)}
        </div>
        <div>
            <LogOut />
        </div>
    </div>;
});
AppMenuStateless.displayName = "AppMenuStateless";

export interface AppMenuItem {
    label: string,
    url: string
}

export type AppMenuProps = {
    menuItems: AppMenuItem[]
}

export default function AppMenu({menuItems}: AppMenuProps) {
    const pathname = usePathname();
    const items = menuItems.map(item => ({
        ...item,
        active: item.url.startsWith(pathname + '/') || item.url === pathname
    }))

    return <AppMenuStateless menuItems={items}/>;
}