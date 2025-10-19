import React from 'react'

const Navigationbar = () => {
    return (
        <div className={"navigationcontainer"}>
            <ul>
                <li>
                    <a href="/rent">Rent a car</a>
                </li>
                <li>
                    <a href="/admin">Admin</a>
                </li>
            </ul>

        </div>
    )
}

export default Navigationbar;