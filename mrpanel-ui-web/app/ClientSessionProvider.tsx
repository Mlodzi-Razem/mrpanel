"use client";

import { SessionProvider } from "next-auth/react";
import React from "react";

/**
 * To be used in server components.
 */
export default function ClientSessionProvider({children}: { children: React.ReactNode }) {
    return <SessionProvider>{children}</SessionProvider>;
}