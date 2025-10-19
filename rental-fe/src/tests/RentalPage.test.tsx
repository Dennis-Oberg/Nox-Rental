import React, { PropsWithChildren } from "react";
import { render, screen, waitFor } from "@testing-library/react";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { MemoryRouter } from "react-router";
import RentalPage from "../pages/RentalPage";
import Navigationbar from "../components/Navigationbar";

jest.mock("../hooks/apiHooks", () => ({
    useGetCarsData: jest.fn(),
    usePutRentalMutation: jest.fn(),
}));

import { useGetCarsData, usePutRentalMutation } from "../hooks/apiHooks";

const wrapper = ({ children }: PropsWithChildren) => {
    const queryClient = new QueryClient({
        defaultOptions: { queries: { retry: false } },
    });
    return (
        <MemoryRouter initialEntries={["/"]}>
            <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>
        </MemoryRouter>
    );
};

beforeEach(() => {
    jest.clearAllMocks();
});

test("renders rent a car text in RentalPage", async () => {
    (useGetCarsData as jest.Mock).mockReturnValue({
        isLoading: false,
        data: [
            { id: 1, carName: "Volvo S60", pricePerDay: 1500 },
            { id: 2, carName: "Ford Focus", pricePerDay: 1200 },
        ],
    });

    (usePutRentalMutation as jest.Mock).mockReturnValue({
        mutateAsync: jest.fn(),
        isPending: false,
    });

    render(<RentalPage />, { wrapper });

    await waitFor(() => {
        expect(screen.getByText(/Rent a car!/i)).toBeInTheDocument();
    });

    expect(screen.getByText(/Volvo S60/i)).toBeInTheDocument();
    expect(screen.getByText(/Ford Focus/i)).toBeInTheDocument();
});

test("renders the navigationbar", () => {
    render(<Navigationbar />);
    expect(screen.getByText(/Rent a car/i)).toBeInTheDocument();
    expect(screen.getByText(/Admin/i)).toBeInTheDocument();
});
