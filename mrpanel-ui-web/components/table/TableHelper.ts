import { ColumnDef } from "@tanstack/react-table";

export function buildColumnsFromHeaders(headernames : string[]): ColumnDef<string>[] {
    return headernames.map((h) => ({
        header: h,
        accessorFn: (row: string) => {
            try {
                const obj = JSON.parse(row);
                return obj == null ? undefined : obj[h as typeof obj];
            } catch {
                return ""
            }
        }
    }));
}