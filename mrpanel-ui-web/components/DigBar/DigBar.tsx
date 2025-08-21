"use client"
import React from 'react';
import {useSession} from "next-auth/react";
import SignInButton from "@/components/SignInButton/SignInButton";
import styles from './DigBar.module.scss'

const DigBar = () => {
    const { data: session } = useSession();

    return (
        <div className={styles['crackowanie-freshmanow']}>
            <SignInButton/>
            {session?.user?.name && (
                <p className={styles.greeter}>
                    Witaj {session.user.name}
                </p>
            )}
            {session?.user?.image && (
                <img
                    className={styles.avatar}
                    src={session.user.image}
                    alt={`Awatar - ${session.user.name}`}
                />
            )}
        </div>
    );
}

export default DigBar;