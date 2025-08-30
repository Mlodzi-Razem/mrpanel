import React, { Suspense } from "react";
import AppMenu from "@/components/layout/app-menu/AppMenu";
import Spinner from "@/components/loader/Spinner";
import { MENU_ITEMS } from "@/app/menu-items";
import getDefaultServerSession from "@/hooks/getDefaultServerSession";
import SignInScreen from "@/components/layout/sign-in-screen/SignInScreen";
import ClientSessionAwaiter from "@/components/session/ClientSessionAwaiter";

const Loading = () => <div style={{
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    width: '100%',
    height: '100%'
}}><Spinner/></div>;

export default async function AuthenticatedLayout({children}: React.PropsWithChildren) {
    const session = await getDefaultServerSession();

    if (!session || !session.user) {
        return <SignInScreen/>
    }

    return <ClientSessionAwaiter fallback={<Loading/>}>
        <div style={{display: 'grid', gridTemplateRows: 'auto 1fr auto', minHeight: '100vh', width: '100%'}}>
            <AppMenu menuItems={MENU_ITEMS}/>
            <div>
                <Suspense fallback={<Loading/>}>
                    {children}
                </Suspense>
            </div>
            <div>
                {/*TODO: Footer?*/}
            </div>
        </div>
    </ClientSessionAwaiter>;
}