'use client';

import { useMemo } from "react";

const DEFAULT_RESULT = 16 as const;
export default function useDefaultFontSize(): number {
    return useMemo(() => {
        if (!window) {
            return DEFAULT_RESULT;
        }

        const element = document.createElement('div');
        element.style.width = '1rem';
        element.style.display = 'none';
        document.body.append(element);

        const widthMatch =
            window.getComputedStyle(element)
                  .getPropertyValue('width')
                  .match(/\d+/);

        element.remove();

        if (!widthMatch || widthMatch.length < 1) {
            return DEFAULT_RESULT;
        }

        const result = Number(widthMatch[0]);
        return !isNaN(result) ? result : DEFAULT_RESULT;
    }, [window]);
}