import React from "react";
import styles from "./MRCheckbox.module.scss";

interface MRCheckboxProps {
  isChecked: boolean;
  onCheckedChange: (checked: boolean) => void;
  disabled?: boolean;
  label?: string;
}

const MRCheckbox = ({ 
  isChecked, 
  onCheckedChange, 
  disabled = false,
  label 
}: MRCheckboxProps) => {
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    onCheckedChange(e.target.checked);
  };

  return (
    <label className={styles["mr-checkbox-wrapper"]}>
      <input
        type="checkbox"
        checked={isChecked}
        onChange={handleChange}
        disabled={disabled}
        className={styles["mr-checkbox-input"]}
      />
      <span className={styles["mr-checkbox-custom"]}>
        {isChecked && (
          <svg
            width="12"
            height="10"
            viewBox="0 0 12 10"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M1 5L4.5 8.5L11 1"
              stroke="currentColor"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
            />
          </svg>
        )}
      </span>
      {label && <span className={styles["mr-checkbox-label"]}>{label}</span>}
    </label>
  );
};

export default MRCheckbox;