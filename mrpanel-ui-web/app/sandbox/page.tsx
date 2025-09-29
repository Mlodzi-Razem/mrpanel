"use client"
import { useMemo } from "react";
import MRTable from "@/components/table/Table"
import { type MRTableProps } from "@/components/table/Table"
import { ColumnDef } from "@tanstack/react-table";

type Member = {
  firstName: string;
  lastName: string;
  age: number;
  visits: number;
  progress: number;
  status: string;
};

export const Sandbox = () => {
    const members: Member[] = useMemo(
    () => [
      { firstName: "Alice", lastName: "Smith", age: 28, visits: 4, progress: 50, status: "active" },
      { firstName: "Bob", lastName: "Jones", age: 34, visits: 10, progress: 80, status: "inactive" },
    ],
    []
  );

  const data = useMemo<string[]>(
    () => members.map((m) => JSON.stringify(m)),
    [members]
  );

    const columns = ["firstName", "lastName", "age", "visits", "progress", "status"];
    
    return(
        <div>
            <MRTable data={data} columns={columns} table_name={"asd"}></MRTable>
        </div>
    )
}

export default Sandbox