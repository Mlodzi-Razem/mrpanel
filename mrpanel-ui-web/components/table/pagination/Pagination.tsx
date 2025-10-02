"use client";

import React from "react";
import styles from "@/components/table/Table.module.scss";
import { PaginationProps, PaginationButtonProps, NavigationButtonProps } from "../Table.types";

/**
 * Calculates which page numbers to show with ThreeDots
 * Example: [1, ..., 5, 6, 7, ..., 20] for currentPage=6
 */
function calculateVisiblePages(pages: number[], currentPage: number, maxVisible: number): number[] {
  if (pages.length <= maxVisible) {
    return pages;
  }

  const halfVisible = Math.floor(maxVisible / 2);
  let start = Math.max(1, currentPage - halfVisible);
  let end = Math.min(pages.length, currentPage + halfVisible);

  if (currentPage <= halfVisible) {
    end = maxVisible;
  } else if (currentPage >= pages.length - halfVisible) {
    start = pages.length - maxVisible + 1;
  }

  return pages.slice(start - 1, end);
}

/**
 * Clickable page number button
 */
function PaginationButton({ page, isActive, onClick }: PaginationButtonProps) {
  return (
    <button
      type="button"
      className={`${styles["mr-pagination-button"]} ${
        isActive ? styles["mr-pagination-button--active"] : ""
      }`}
      onClick={() => onClick(page)}
      aria-current={isActive ? "page" : undefined}
    >
      {page}
    </button>
  );
}

/**
 * Previous/Next navigation button
 */
function NavigationButton({ direction, disabled, onClick }: NavigationButtonProps) {
  const label = direction === "prev" ? "Poprzednia strona" : "Następna strona";
  const symbol = direction === "prev" ? "‹" : "›";

  return (
    <button
      type="button"
      className={styles["mr-pagination-button"]}
      onClick={onClick}
      disabled={disabled}
      aria-label={label}
    >
      {symbol}
    </button>
  );
}

/**
 * ThreeDots (...) to indicate hidden pages
 */
function ThreeDots() {
  return <span className={styles["mr-pagination-ThreeDots"]}>...</span>;
}

/**
 * Pagination - Stateless pagination component
 * No knowledge of table - only manages page numbers and callbacks
 * Supports ThreeDots for large page counts
 */
export default function Pagination({
  pages,
  currentPage,
  onPageChange,
  onPrev,
  onNext,
}: PaginationProps) {
  const maxVisible = 7;
  const visiblePages = calculateVisiblePages(pages, currentPage, maxVisible);
  const firstPage = 1;
  const lastPage = pages.length;
  const showFirstPage = visiblePages[0] > firstPage;
  const showLastPage = visiblePages[visiblePages.length - 1] < lastPage;
  const showFirstThreeDots = showFirstPage && visiblePages[0] > firstPage + 1;
  const showLastThreeDots = showLastPage && visiblePages[visiblePages.length - 1] < lastPage - 1;

  return (
    <nav aria-label="Paginacja" className={styles["mr-pagination"]}>
      <NavigationButton
        direction="prev"
        disabled={currentPage <= 1}
        onClick={onPrev}
      />

      {showFirstPage && (
        <PaginationButton
          page={firstPage}
          isActive={false}
          onClick={onPageChange}
        />
      )}

      {showFirstThreeDots && <ThreeDots />}

      {visiblePages.map((page) => (
        <PaginationButton
          key={page}
          page={page}
          isActive={page === currentPage}
          onClick={onPageChange}
        />
      ))}

      {showLastThreeDots && <ThreeDots />}

      {showLastPage && (
        <PaginationButton
          page={lastPage}
          isActive={false}
          onClick={onPageChange}
        />
      )}

      <NavigationButton
        direction="next"
        disabled={currentPage >= lastPage}
        onClick={onNext}
      />
    </nav>
  );
}