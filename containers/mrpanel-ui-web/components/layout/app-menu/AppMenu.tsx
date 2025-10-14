'use client';

import {memo} from "react";
import Link from "next/link";
import styles from './AppMenu.module.scss';
import classes from "@/util/classes";
import {usePathname} from "next/navigation";
import LoggedUserAvatar from "@/components/layout/app-menu/LoggedUserAvatar";

interface MenuItem {
    label: string,
    url: string,
    active: boolean
}

const AppMenuItem = memo((props: { item: MenuItem }) => {
    const {active, url, label} = props.item;
    return <Link className={classes(styles.menuItem, {[styles.menuItemActive]: active})}
                 href={url}
                 aria-disabled={active}
                 prefetch={false}>{label}</Link>
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
            <LoggedUserAvatar/>
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

function matchUrl(item: AppMenuItem, pathname: string) {
    return pathname.startsWith(item.url + '?') || item.url === pathname;
}

export default function AppMenu({menuItems}: AppMenuProps) {
    const pathname = usePathname();
    const items = menuItems.map(item => ({
        ...item,
        active: matchUrl(item, pathname)
    }))

    return <AppMenuStateless menuItems={items}/>;
}