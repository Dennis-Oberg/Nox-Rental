import React from "react";

interface LoadingProps {
    loadingText?: string
}
const Loading = (props: LoadingProps) => {
    return(
        <div className={"parent"}>
            {props.loadingText ?? "Please wait."}
        </div>
    )
}

export default Loading;