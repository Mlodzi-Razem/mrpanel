import { getServerSession, Session } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";

export default function useServerSession(): Promise<Session | null> {
    return getServerSession(authOptions);
}