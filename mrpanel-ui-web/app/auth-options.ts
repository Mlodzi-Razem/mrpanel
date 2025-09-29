import { AuthOptions } from "next-auth";
import GoogleProvider from "next-auth/providers/google";

export const authOptions: AuthOptions = {
    secret: process.env.NEXTAUTH_SECRET!,
    providers: [
        GoogleProvider({
            clientId: process.env.GOOGLE_CLIENT_ID!,
            clientSecret: process.env.GOOGLE_CLIENT_SECRET!,
            authorization: {
                params: {
                    prompt: "select_account",           // this code is needed, so we're not taking a piss with this logging
                }
            }
        }),
    ],
};