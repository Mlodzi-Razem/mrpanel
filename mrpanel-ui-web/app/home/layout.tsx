import React from "react";
import { redirect } from "next/navigation";

export default function AuthenticatedLayout({children}: React.PropsWithChildren) {
    const isLoggedIn = false; // TODO: Implement

    if (!isLoggedIn) {
        redirect('/login');
    }

    return <>
        {children}
    </>;
}