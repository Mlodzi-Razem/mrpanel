import { getServerSession, Session } from "next-auth";

import { authOptions } from "@/app/auth-options";

export default function getDefaultServerSession(): Promise<Session | null> {
    return getServerSession(authOptions);
}