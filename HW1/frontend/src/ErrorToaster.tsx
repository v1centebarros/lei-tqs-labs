import {useState} from "react";

export function ErrorToaster(props: { errorText: string, setError: (value: boolean) => void }) {

    const [show, setShow] = useState(true);
    setTimeout(() => {
        setShow(false);
        props.setError(false);
    }, 3000);


    return show && (
        <div className="toast toast-end">
            <div className="alert alert-error">
                <div>
                    <span>{props.errorText}</span>
                </div>
            </div>
        </div>);
}