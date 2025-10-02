import React from "react";
import styles from "./Table.module.scss";
import { TableHeaderProps } from "./Table.types";

function SelectionHeaderCell() {
  return (
    <div
      className={`${styles["mr-header-cell"]} ${styles["mr-header-cell--checkbox"]}`}
      aria-hidden="true"
    />
  );
}

function HeaderCell({ children }: { children: React.ReactNode }) {
  return <div className={styles["mr-header-cell"]}>{children}</div>;
}

const TableHeader = <Row,>({
  headers,
  showSelection = false,
}: TableHeaderProps<Row>) => {
  return (
    <div className={styles["mr-table-head"]}>
      <div className={styles["mr-row"]}>
        {showSelection && <SelectionHeaderCell />}
        {headers.map((col) => (
          <HeaderCell key={String(col.id)}>{col.header}</HeaderCell>
        ))}
      </div>
    </div>
  );
};

export default TableHeader;
