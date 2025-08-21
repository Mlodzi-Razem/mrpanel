"use client";
import React from 'react';
import { signIn, signOut, useSession } from 'next-auth/react';
import styles from './SignInButton.module.scss';



const SignInButton = () => {
    const {data: session} = useSession();

    if (session && session.user) {
        return (
            <button onClick={() => signOut()} className={styles.button}>
                Wyloguj się
            </button>
        );
    }

    return (
        <button onClick={() => signIn()} className={styles.button}>
            Zaloguj się
        </button>
    );
};

export default SignInButton;
