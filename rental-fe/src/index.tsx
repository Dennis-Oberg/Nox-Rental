import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import {createBrowserRouter, RouterProvider} from "react-router";
import RentalPage from "./pages/RentalPage";
import Navigationbar from "./components/Navigationbar";
import AdminPage from "./pages/AdminPage";

const router = createBrowserRouter([
    {
        path: "/",
        element: <App/>,
    },
    {
        path: "/rent",
        element: <RentalPage/>,
    },
    {
        path: "/admin",
        element: <AdminPage/>
    }

])
const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);
const client = new QueryClient()
root.render(
    <QueryClientProvider client={client}>
        <Navigationbar/>
        <RouterProvider router={router}/>
    </QueryClientProvider>
);


