import { Table } from "@tanstack/react-table";
import {flexRender} from "@tanstack/react-table";
import MRCheckbox from "./Checkbox/MRCheckbox";




interface TableHeaderProps<TData> {
  table: Table<TData>;
}

const TableBody = <TData,>({ table }: TableHeaderProps<TData>) => {
    return (
        <tbody>
            {table.getRowModel().rows.length === 0 ? (
                <tr>
                    <td colSpan={table.getAllLeafColumns().length + 1}>
                        Brak danych
                    </td>
                </tr>
            ) : (
                table.getRowModel().rows.map((row) => (
                    <tr key={row.id}>
                        {row.getVisibleCells().map((cell) => (
                            <td key={cell.id} style={{ padding: 8 }}>
                                {flexRender(cell.column.columnDef.cell, cell.getContext())}
                            </td>
                        ))}
                        <td style={{ width: 0, marginRight: "5%"}}>
                            <MRCheckbox
                                isChecked={row.getIsSelected()}
                                onCheckedChange={(checked) => row.toggleSelected(checked)}
                            />
                        </td>
                    </tr>
                ))
            )}
        </tbody>
    );
};

export default TableBody;