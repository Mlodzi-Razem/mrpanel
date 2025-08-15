import React, { Suspense } from "react";
import { redirect } from "next/navigation";
import AppMenu, { AppMenuItem } from "@/components/layout/app-menu/AppMenu";
import Spinner from "@/components/loader/Spinner";

const MENU_ITEMS: AppMenuItem[] = [
    {label: 'Home', url: '/home'},
    {label: 'Users', url: '/users'}
];

const Loading = () => <div style={{
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    width: '100%',
    height: '100%'
}}><Spinner/></div>;

export default function AuthenticatedLayout({children}: React.PropsWithChildren) {
    const isLoggedIn = true; // TODO: Implement

    if (!isLoggedIn) {
        redirect('/login');
    }

    return <>
        <div style={{display: 'grid', gridTemplateRows: 'auto 1fr auto', minHeight: '100vh', width: '100%'}}>
            <AppMenu menuItems={MENU_ITEMS}/>
            <div style={{padding: '1.5rem'}}>
                <Suspense fallback={<Loading/>}>
                    {children}
                </Suspense>
            </div>
            <div>
                {/*TODO: Footer?*/}
            </div>
        </div>

    </>;
}