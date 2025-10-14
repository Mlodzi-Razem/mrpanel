"use client";

import {signIn} from "next-auth/react";

export default function SignInScreen() {
    signIn();

    return <div/>;
}