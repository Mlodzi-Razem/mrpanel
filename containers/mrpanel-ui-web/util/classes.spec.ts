import {expect} from "@jest/globals";
import classes from "./classes";

describe('classes', () => {
    it('should return empty string on no arguments', () => {
        expect(classes()).toBe('');
    })

    it('should return a single class name', () => {
        expect(classes('foo')).toBe('foo');
    })

    it('should return multiple class names', () => {
        expect(classes('foo', 'bar', 'baz')).toBe('foo bar baz');
    })

    it('should filter classes', () => {
        expect(classes({a: true, b: false, c: true})).toBe('a c');
    })

    it('should work with mixed arguments', () => {
        expect(classes('foo', {a: true, b: false, c: true})).toBe('foo a c');
    })
})