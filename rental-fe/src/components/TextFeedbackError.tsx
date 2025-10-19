import React from "react";

interface Props {
    text?: string;
    textColor?: string;
}

const TextFeedbackError = (props: Props) => {
    return (
        <>
        <span
            className={!props.textColor ? 'errorText' : undefined}
            style={props.textColor ? { color: props.textColor } : undefined}
        >
            {props.text ?? "Generic error"}
        </span>
        </>
    )
}

export default TextFeedbackError;