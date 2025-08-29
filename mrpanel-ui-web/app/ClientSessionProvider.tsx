"use client";

import { SessionProvider } from "next-auth/react";
import React from "react";

/**
 * To be used in server components.
 */
export default function ClientSessionProvider({children}: React.PropsWithChildren<{}>) {
    return <SessionProvider>{children}</SessionProvider>;
}