import DashboardCustomizeRoundedIcon from '@mui/icons-material/DashboardCustomizeRounded';
import BookOnlineRoundedIcon from '@mui/icons-material/BookOnlineRounded';
import AdminPanelSettingsRoundedIcon from '@mui/icons-material/AdminPanelSettingsRounded';

const SideBarData = [   
    {
        title: 'Dashboard',
        path: '/',
        icon: <DashboardCustomizeRoundedIcon />
    },
    {
        title: 'Tickets',
        path: 'Tickets',
        icon: <BookOnlineRoundedIcon />
    },
    {
        title: 'Administration',
        path: 'Administration',
        icon: <AdminPanelSettingsRoundedIcon />
    },
]

export default SideBarData