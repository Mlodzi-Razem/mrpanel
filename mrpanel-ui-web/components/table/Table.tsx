"use client";

import React from "react";
import styles from "./Table.module.scss";
import TableHeader from "./TableHeader";
import TableBody from "./TableBody";
import Pagination from "./pagination/Pagination";
import { MRTableProps } from "./Table.types";

function buildGridColumnTemplate(showSelection: boolean, columnCount: number): string {
  const selectionColumn = showSelection ? "max-content " : "";
  const dataColumns = Array(columnCount).fill("minmax(0,1fr)").join(" ");
  return selectionColumn + dataColumns;
}

function countSelectedRows(selectedRowIds?: Set<string> | string[]): number {
  if (!selectedRowIds) return 0;
  if (selectedRowIds instanceof Set) return selectedRowIds.size;
  return Array.isArray(selectedRowIds) ? selectedRowIds.length : 0;
}

function createScrollHandler(onScrollBeyondLastRow: () => void) {
  return (e: React.UIEvent<HTMLDivElement>) => {
    const target = e.currentTarget;
    const scrollTop = target.scrollTop;
    const scrollHeight = target.scrollHeight;
    const clientHeight = target.clientHeight;
    const threshold = 10;

    if (scrollTop + clientHeight >= scrollHeight - threshold) {
      onScrollBeyondLastRow();
    }
  };
}

/**
 * Generates array of page numbers [1, 2, 3, ..., totalPages]
 */
function generatePageNumbers(totalPages: number): number[] {
  return Array.from({ length: totalPages }, (_, i) => i + 1);
}

function SelectionInfo({ count }: { count: number }) {
  return (
    <div className={styles["mr-selection-info"]}>
      Wybrano {count} wierszy
    </div>
  );
}

/**
 * MRTable - Stateless table component using CSS Grid + Subgrid

 * - No internal state - renders purely from props - 
 * - Uses CSS Grid for layout with subgrid for column alignment
 * - Supports offset pagination (via pagination prop)
 * - Supports cursor pagination (via onScrollBeyondLastRow callback)
 * - Custom cell renderers via column.renderCell
 * - Optional row selection with checkboxes
 */
export default function MRTable<Row = Record<string, unknown>>({
  data,
  columns,
  table_name,
  showSelection = false,
  selectedRowIds,
  onRowToggle,
  getRowId = (_row, index) => String(index),
  pagination,
  onScrollBeyondLastRow,
}: MRTableProps<Row>) {
  const columnTemplate = buildGridColumnTemplate(showSelection, columns.length);
  const selectedCount = countSelectedRows(selectedRowIds);
  const handleScroll = onScrollBeyondLastRow ? createScrollHandler(onScrollBeyondLastRow) : undefined;

  return (
    <div className={styles["mr-table-layout"]}>
      {showSelection && <SelectionInfo count={selectedCount} />}

      <div
        className={`${styles["mr-table"]} ${table_name || ""}`}
        style={{ gridTemplateColumns: columnTemplate }}
      >
        <TableHeader headers={columns} showSelection={showSelection} />

        <TableBody
          data={data}
          columns={columns}
          showSelection={showSelection}
          selectedRowIds={selectedRowIds}
          onRowToggle={onRowToggle}
          getRowId={getRowId}
          onScroll={handleScroll}
        />
      </div>

      {pagination && (
        <Pagination
          pages={generatePageNumbers(pagination.totalPages)}
          currentPage={pagination.currentPage}
          onPageChange={pagination.onPageChange}
          onPrev={() => pagination.onPageChange(Math.max(1, pagination.currentPage - 1))}
          onNext={() => pagination.onPageChange(Math.min(pagination.totalPages, pagination.currentPage + 1))}
        />
      )}
    </div>
  );
}