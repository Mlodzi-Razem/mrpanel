"use client"
import styles from "./Table.module.scss"
import TableHeader from "./TableHeader";
import { buildColumnsFromHeaders } from "./TableHelper";
import React, { useState } from "react";
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
    const paginationFallback= { pageIndex: 0, pageSize: 10}

    const [rowSelection, setRowSelection] = useState({})

    const headers = useMemo(() => buildColumnsFromHeaders(columns), [columns]);

    const table = useReactTable({
        columns: headers,
        data,
        getCoreRowModel: getCoreRowModel(),
        getFilteredRowModel: filter ? getFilteredRowModel() : undefined,
        onRowSelectionChange: setRowSelection, //hoist up the row selection state to your own scope
        state: {
        rowSelection, //pass the row selection state back to the table instance
  },
        getPaginationRowModel: pagination ? getPaginationRowModel() : undefined
  });
    console.log(Object.keys(table.getState().rowSelection).length ?? 0)
    const pageCount = pagination ? table.getPageCount() : 1;
    const { pageIndex, pageSize } = table.getState().pagination ?? paginationFallback;
    const [HowManySelected, ChangeHowManySelected] = useState(0);
    const rows = table.getState().rowSelection;

    useMemo(() => {
        const selectedCount = Object.keys(rows).length;
        ChangeHowManySelected(selectedCount);
    }, [rows]);
  return(
        <div className={table_name} style={{margin: "5%"}}>
            
            <div className={styles["mr-table-layout"]}>
                
                {pagination && (
                  <div className={styles["mr-table-toolbar"]}>
                    <div className={styles["mr-table-controls-left"]}>
                      <label className={styles["mr-sr-only"]} htmlFor="mr-pagination-select">Page</label>
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

                    <div className={styles["mr-table-controls-right"]}>
                      <label className={styles["mr-sr-only"]} htmlFor="mr-page-size-select">Wpisy na strone</label>
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
                    <p>Wybrano {HowManySelected} wierszy</p>
                    <table className={styles["tableContainer-default"]}> 
                        {/* i want to make this ^ overridable at some point  */}
                        <TableHeader table={table}/>
                        <TableBody table={table}/>
                    </table>
                </div>
            </div>
        </div>
    )
}