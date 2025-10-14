import {getServerSession, Session} from "next-auth";

import {authOptions} from "@/app/auth-options";

export class NoSessionError extends Error {
    constructor(message?: string) {
        super(message ?? "No session found");
    }
}

export type AuthenticatedSession = Omit<Session, 'user'> & { user: { name: string, email: string } };

export default async function getDefaultServerSession(): Promise<AuthenticatedSession> {
    const session = await getServerSession(authOptions);

    if (!session || !session.user) {
        throw new NoSessionError();
    }

    if (!session.user.email || !session.user.name) {
        throw new NoSessionError("No user info found in session")
    }

    return session as AuthenticatedSession;
}