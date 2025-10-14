"use client";

import classes from "@/util/classes";

import styles from './Avatar.module.scss';
import {useState} from "react";
import Image from "next/image";
import {UserCircle} from "lucide-react";
import useDefaultFontSize from "@/hooks/useDefaultFontSize";

export type AvatarSize = 'small' | 'medium' | 'large';

export type AvatarProps = {
    image: string | undefined | null,
    size?: AvatarSize,
    onClick?: () => void
};

const Avatar = (props: AvatarProps) => {
    const [hasError, setHasError] = useState(false);

    const size = props.size ?? 'medium';

    const remSize = useDefaultFontSize();
    const sizeInPixels = size === 'small' ? 2 * remSize : size === 'medium' ? 2.5 * remSize : 3 * remSize;

    const className = classes(styles.avatar, styles[`avatar-${size}`], {[styles.clickable]: !!props.onClick});

    if (hasError || !props.image) {
        return <UserCircle className={className} onClick={props.onClick} size={sizeInPixels}/>
    }


    return <Image alt="Avatar"
                  className={className}
                  onClick={props.onClick}
                  src={props.image}
                  width={sizeInPixels}
                  height={sizeInPixels}
                  onError={() => setHasError(true)}/>;
};

export default Avatar;