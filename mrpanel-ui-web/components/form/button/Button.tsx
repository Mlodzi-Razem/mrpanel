import React, { forwardRef, memo } from "react";

import styles from './Button.module.scss';
import classes from "@/util/classes";

export type ButtonFlavor = 'primary' | 'secondary';

export type ButtonProps = React.PropsWithChildren<{
    flavor?: ButtonFlavor;
    style?: React.CSSProperties;
    className?: string;
    active?: boolean;
    onClick?: () => void;
    type?: 'button' | 'submit' | 'reset';
}>

const Button = memo(forwardRef<HTMLButtonElement, ButtonProps>((
    {children, flavor, className, style, active, onClick, type},
    ref
) => {
    flavor ??= "primary";
    active ??= true;
    type ??= "button";

    const buttonClass = classes(
        styles.button,
        {
            [styles.primary]: flavor === "primary",
            [styles.secondary]: flavor === "secondary",
            [styles.disabled]: !active
        },
        className
    );

    return <button className={buttonClass}
                   style={style}
                   onClick={onClick}
                   ref={ref}
                   type={type}>
        {children}
    </button>;
}));
Button.displayName = 'Button';

export default Button;
