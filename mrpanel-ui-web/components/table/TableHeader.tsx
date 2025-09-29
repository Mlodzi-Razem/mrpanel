import { Table } from "@tanstack/react-table";
import {flexRender} from "@tanstack/react-table";

interface TableHeaderProps<TData> {
  table: Table<TData>;
}

const TableHeader = <TData,>({ table }: TableHeaderProps<TData>) => {
  return (
    <thead>
      {table.getHeaderGroups().map((headerGroup) => (
        <tr key={headerGroup.id}>
          {headerGroup.headers.map((header) => (
            <th key={header.id}>
              {header.isPlaceholder ? null : (
                <div>
                  {flexRender(header.column.columnDef.header, header.getContext())}
                </div>
              )}
            </th>
          ))}
        </tr>
      ))}
    </thead>
  );
};

export default TableHeader;
