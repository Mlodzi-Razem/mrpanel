import styles from './VerticalDivider.module.scss';

export default function VerticalDivider({height}: { height?: string | number }) {
    return <div className={styles.verticalDivider} style={{height: height ?? '100%'}}/>;
}