import type {Metadata} from "next";
import {Lato} from "next/font/google";
import "./globals.scss";
import React, {Suspense} from "react";
import Spinner from "@/components/loader/Spinner";
import ClientSessionProvider from "@/app/ClientSessionProvider";

const lato = Lato({
    variable: "--font-lato",
    subsets: ["latin", "latin-ext"],
    weight: ["300", "400", "100", "700", "900"],
});


export const metadata: Metadata = {
    title: "MR Panel",
    description: "Member management panel for Młodzi Razem",
};

const Loading = () => <div style={{
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    width: '100%',
    height: '100%'
}}><Spinner/></div>;

export default function RootLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <html lang="pl">
            <body className={`${lato.className}`}>
                <Suspense fallback={<Loading/>}>
                    <ClientSessionProvider>
                        {children}
                    </ClientSessionProvider>
                </Suspense>
            </body>
        </html>
    );
}
