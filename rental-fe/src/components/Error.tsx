import React from "react";

interface ErrorProps {
    errorText?: string;
}

const Error = (props: ErrorProps) => {
    return(
        <div className={"parent"}>
            {props.errorText ?? ""}
        </div>
    )
}

export default Error;