"use client";

import { signOut, useSession } from "next-auth/react";
import Avatar from "@/components/layout/avatar/Avatar";
import HoverMenu from "@/components/layout/menu/HoverMenu";

export default function LoggedUserAvatar() {
    const session = useSession({required: true});

    return <HoverMenu trigger={<Avatar image={session.data!.user?.image}
                                       onClick={() => {}}/>}>
        <HoverMenu.Item onClick={signOut}>Wyloguj się</HoverMenu.Item>
        <HoverMenu.Item>Test</HoverMenu.Item>
    </HoverMenu>;
}