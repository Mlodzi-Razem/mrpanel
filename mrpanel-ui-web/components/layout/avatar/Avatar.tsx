"use client";

import classes from "@/util/classes";

import styles from './Avatar.module.scss';
import { useState } from "react";
import Image from "next/image";
import { UserCircle } from "lucide-react";

export type AvatarSize = 'small' | 'medium' | 'large';

export type AvatarProps = {
    image: string,
    size?: AvatarSize,
    onClick?: () => void
};

const Avatar = (props: AvatarProps) => {
    const [hasError, setHasError] = useState(false);

    const size = props.size ?? 'medium';
    const className = classes(styles.avatar, styles[`avatar-${size}`], {[styles.clickable]: !!props.onClick});

    if (hasError) {
        return <UserCircle className={className} onClick={props.onClick}/>
    }

    return <img alt="Avatar"
                className={className}
                onClick={props.onClick}
                src={props.image}
                onError={() => setHasError(true)}/>;
};

export default Avatar;