import React from "react";
import MRCheckbox from "./Checkbox/MRCheckbox";
import styles from "./Table.module.scss";
import { TableBodyProps, MRTableColumn, SelectionCellProps, TableRowProps } from "./Table.types";

function isRowSelected(selectedRowIds: Set<string> | string[] | undefined, rowId: string): boolean {
  if (!selectedRowIds) return false;
  if (selectedRowIds instanceof Set) return selectedRowIds.has(rowId);
  return Array.isArray(selectedRowIds) ? selectedRowIds.includes(rowId) : false;
}

function renderCellContent<Row>(row: Row, column: MRTableColumn<Row>): React.ReactNode {
  if (column.renderCell) {
    return column.renderCell(row);
  }
  const value = (row as Record<string, unknown>)?.[String(column.id)];
  return String(value ?? "");
}

/**
 * Empty state when no data
 */
function EmptyTableBody() {
  return (
    <div className={styles["mr-table-body"]}>
      <div className={styles["mr-row"]}>
        <div className={styles["mr-cell"]} style={{ gridColumn: "1 / -1" }}>
          Brak danych
        </div>
      </div>
    </div>
  );
}

/**
 * Cell with checkbox for row selection
 */
function SelectionCell({ isChecked, onToggle }: SelectionCellProps) {
  return (
    <div className={`${styles["mr-cell"]} ${styles["mr-cell--checkbox"]}`}>
      <MRCheckbox isChecked={isChecked} onCheckedChange={onToggle} />
    </div>
  );
}

function DataCell({ children }: { children: React.ReactNode }) {
  return <div className={styles["mr-cell"]}>{children}</div>;
}

/**
 * Single table row with optional checkbox + data cells
 */
function TableRow<Row>({
  row,
  rowId,
  columns,
  showSelection,
  isSelected,
  onToggle,
}: TableRowProps<Row>) {
  return (
    <div className={styles["mr-row"]}>
      {showSelection && (
        <SelectionCell
          isChecked={isSelected}
          onToggle={(checked) => onToggle?.(rowId, checked)}
        />
      )}

      {columns.map((col) => (
        <DataCell key={String(col.id)}>{renderCellContent(row, col)}</DataCell>
      ))}
    </div>
  );
}

const TableBody = <Row,>({
  data,
  columns,
  showSelection = false,
  selectedRowIds,
  onRowToggle,
  getRowId,
  onScroll,
}: TableBodyProps<Row>) => {
  if (data.length === 0) {
    return <EmptyTableBody />;
  }

  return (
    <div className={styles["mr-table-body"]} onScroll={onScroll}>
      {data.map((row, index) => {
        const rowId = getRowId(row, index);
        const isSelected = isRowSelected(selectedRowIds, rowId);

        return (
          <TableRow
            key={rowId}
            row={row}
            rowId={rowId}
            columns={columns}
            showSelection={showSelection}
            isSelected={isSelected}
            onToggle={onRowToggle}
          />
        );
      })}
    </div>
  );
};

export default TableBody;