"use client";

import React from "react";
import {useSession} from "next-auth/react";

/**
 * Waits for the client session to initialize. Shows fallback in the meantime.
 */
export default function ClientSessionAwaiter({children, fallback}: {children: React.ReactNode, fallback: React.ReactNode}) {
    const session = useSession({ required: true });

    if (!session || session.status === 'loading') {
        return fallback;
    }

    return children;
}