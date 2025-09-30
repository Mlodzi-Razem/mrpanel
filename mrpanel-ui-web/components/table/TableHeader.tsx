import { Table } from "@tanstack/react-table";
import {flexRender} from "@tanstack/react-table";

interface TableHeaderProps<TData> {
  table: Table<TData>;
}

function nullInputHandler(value : unknown) {
  if (value === undefined) return "";
  try{
  const buffer = String(value)
  if(buffer.length == 0) {
    return ""
  }
  else{
    return buffer
  }
}
catch {

}

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
                  {header.column.getCanFilter() ? (
                    <div>
                      <input
                      type="text"
                      value={nullInputHandler(header.column.getFilterValue())}
                      onChange={(e) => header.column.setFilterValue(e.target.value)}
                      placeholder="Filtruj"
                      />
                    </div>
                  ) : null}
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
