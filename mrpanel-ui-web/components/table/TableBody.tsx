import { Table } from "@tanstack/react-table";
import {flexRender} from "@tanstack/react-table";





interface TableHeaderProps<TData> {
  table: Table<TData>;
}

const TableBody = <TData,>({ table }: TableHeaderProps<TData>) => {
  return (
      <tbody>
            {table.getRowModel().rows.length === 0 ? (
                <tr>
                    <td>
                        Brak danych
                    </td>
                </tr>
                ) : (
            table.getRowModel().rows.map((row) => (
                <tr key={row.id}>
                   {row.getVisibleCells().map((cell) => (
                    <td key={cell.id} style={{ padding: 8 }}>

                        {flexRender(cell.column.columnDef.cell, cell.getContext())}

                    </td>    ))}
                </tr>    )))}
        </tbody>
  );
};

export default TableBody;