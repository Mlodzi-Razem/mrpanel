export default function classes(...args: (string | Record<string, boolean> | undefined | null)[]): string {
    return args.flatMap(arg => {
        if (arg === undefined || arg === null) {
            return [];
        }

        if (typeof arg === 'string') {
            return [arg];
        }

        if (typeof arg === 'object') {
            return Object.entries(arg)
                         .filter(([cssClass, enabled]) => enabled)
                         .map(([cssClass, enabled]) => cssClass);
        }

        throw Error("Unsupported arg type: " + typeof arg);
    }).join(" ");
}