import React from 'react';
import {render, screen} from '@testing-library/react';
import Navigationbar from "../components/Navigationbar";

import App from "../App";

test('renders text in app component', () => {
    render(<App/>);
    const textElement = screen.getByText(/You may rent a car or view the admin portal!/i);
    expect(textElement).toBeInTheDocument();
})

test('renders the navigationbar', () => {
    render(<Navigationbar/>);
    const rentTextElement = screen.getByText(/Rent a car/i);
    const adminTextElement = screen.getByText(/Admin/i);
    expect(rentTextElement).toBeInTheDocument();
    expect(adminTextElement).toBeInTheDocument();
});




