import React from "react";


export type MRTableColumn<Row> = {
  id: keyof Row | string;
  header: React.ReactNode;
  renderCell?: (row: Row) => React.ReactNode;
};

export interface MRTablePaginationConfig {
  currentPage: number;
  totalPages: number;
  onPageChange: (page: number) => void;
}

export interface MRTableProps<Row> {
  data: Row[];
  columns: MRTableColumn<Row>[];
  table_name?: string;
  showSelection?: boolean;
  selectedRowIds?: Set<string> | string[];
  onRowToggle?: (rowId: string, selected: boolean) => void;
  getRowId?: (row: Row, index: number) => string;
  pagination?: MRTablePaginationConfig;
  onScrollBeyondLastRow?: () => void;
}

export interface TableHeaderProps<Row> {
  headers: MRTableColumn<Row>[];
  showSelection: boolean;
}

export interface TableBodyProps<Row> {
  data: Row[];
  columns: MRTableColumn<Row>[];
  showSelection: boolean;
  selectedRowIds?: Set<string> | string[];
  onRowToggle?: (rowId: string, selected: boolean) => void;
  getRowId: (row: Row, index: number) => string;
  onScroll?: (e: React.UIEvent<HTMLDivElement>) => void;
}

export interface SelectionCellProps {
  isChecked: boolean;
  onToggle: (checked: boolean) => void;
}

export interface TableRowProps<Row> {
  row: Row;
  rowId: string;
  columns: MRTableColumn<Row>[];
  showSelection: boolean;
  isSelected: boolean;
  onToggle?: (rowId: string, checked: boolean) => void;
}


export interface PaginationProps {
  pages: number[];
  currentPage: number;
  onPageChange: (page: number) => void;
  onPrev?: () => void;
  onNext?: () => void;
}

export interface PaginationButtonProps {
  page: number;
  isActive: boolean;
  onClick: (page: number) => void;
}

export interface NavigationButtonProps {
  direction: "prev" | "next";
  disabled: boolean;
  onClick?: () => void;
}


export interface MRCheckboxProps {
  isChecked: boolean;
  onCheckedChange: (checked: boolean) => void;
  disabled?: boolean;
  label?: string;
}