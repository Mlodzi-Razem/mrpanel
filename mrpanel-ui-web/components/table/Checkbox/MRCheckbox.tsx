import Checkbox from "@mui/material/Checkbox"

interface MRCheckboxProps {
    isChecked: boolean;
    onCheckedChange: (checked: boolean) => void;
}

const MRCheckbox = ({ isChecked, onCheckedChange }: MRCheckboxProps) => {
    const handleIsChecked = (event: React.ChangeEvent<HTMLInputElement>) => {
        onCheckedChange(event.target.checked);
    }
    return (
        <Checkbox onChange={handleIsChecked} checked={isChecked} />
    );
}

export default MRCheckbox;