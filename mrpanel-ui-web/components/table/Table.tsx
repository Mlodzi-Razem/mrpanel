"use client"
import "./Table.css"
import TableHeader from "./TableHeader";
import { buildColumnsFromHeaders } from "./TableHelper";
import React, { useState } from "react";
import { useMemo } from "react";
import { RowModel, RowData, Table } from "@tanstack/react-table";
import {
    useReactTable,
    getCoreRowModel,
    getPaginationRowModel,
    getSortedRowModel,
    getFilteredRowModel,
    SortingState,
    PaginationState,
} from "@tanstack/react-table";
import TableBody from "./TableBody";

export interface MRTableProps {
    data: string[],
    columns: string[],
    table_name: string,
    filter?: boolean,
    pagination?: boolean,
}

export default function MRTable({
  data,
  columns,
  table_name,
  filter = true,
  pagination = true,
}: MRTableProps) {

    const [globalFilter, setGlobalFilter] = useState("");
    const [paginationState, setPaginationState] = useState({ pageIndex: 0, pageSize: 10})

  const headers = useMemo(() => buildColumnsFromHeaders(columns), [columns]);
  const table = useReactTable({
      columns: headers,
      data,
      getCoreRowModel: getCoreRowModel(),
      getFilteredRowModel: filter ? getFilteredRowModel() : undefined,
      getPaginationRowModel: pagination ? getPaginationRowModel() : undefined
  });

  
  return(
        <div className={table_name}>
            <table className="tableContainer-default"> 
                {/* i want to make this ^ overridable at some point  */}
                <TableHeader table={table}/>
                <TableBody table={table}/>
            </table>
        </div>
  )
}