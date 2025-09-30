"use client"
import "./Table.css"
import TableHeader from "./TableHeader";
import { buildColumnsFromHeaders } from "./TableHelper";
import React from "react";
import { useMemo } from "react";
import {
    useReactTable,
    getCoreRowModel,
    getPaginationRowModel,
    //getSortedRowModel,
    getFilteredRowModel,
    //SortingState,
    //PaginationState,
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
    const paginationFallback = { pageIndex: 0, pageSize: 10}

    const headers = useMemo(() => buildColumnsFromHeaders(columns), [columns]);

    const table = useReactTable({
        columns: headers,
        data,
        getCoreRowModel: getCoreRowModel(),
        getFilteredRowModel: filter ? getFilteredRowModel() : undefined,
        getPaginationRowModel: pagination ? getPaginationRowModel() : undefined
  });

    const pageCount = pagination ? table.getPageCount() : 1;
    const { pageIndex, pageSize } = table.getState().pagination ?? paginationFallback;
  
  return(
        <div className={table_name} style={{margin: "5%"}}>

            <div className="mr-table-layout">
                {pagination && (
                  <div className="mr-table-toolbar">
                    <div className="mr-table-controls-left">
                      <label className="mr-sr-only" htmlFor="mr-pagination-select">Page</label>
                      <select
                          id="mr-pagination-select"
                          name="pagination"
                          value={pageIndex}
                          onChange={(e) => table.setPageIndex(Number(e.target.value))}
                      >
                          {Array.from({ length: pageCount }, (_, i) => (
                              <option key={i} value={i}>
                                  Strona {i + 1}
                              </option>
                          ))}
                      </select>
                    </div>

                    <div className="mr-table-controls-right">
                      <label className="mr-sr-only" htmlFor="mr-page-size-select">Wpisy na strone</label>
                      <select
                          id="mr-page-size-select"
                          value={pageSize}
                          onChange={(e) => table.setPageSize(Number(e.target.value))}
                      >
                          {[5, 10, 20, 50, 100].map((s) => (
                              <option key={s} value={s}>{s} / strona</option>
                          ))}
                      </select>
                    </div>
                  </div>
                )}

                <div className="mr-table-wrap">
                    <table className="tableContainer-default"> 
                        {/* i want to make this ^ overridable at some point  */}
                        <TableHeader table={table}/>
                        <TableBody table={table}/>
                    </table>
                </div>
            </div>
        </div>
    )
}