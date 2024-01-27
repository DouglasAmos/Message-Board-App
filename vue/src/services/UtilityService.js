export default {
    formatDate(dateString) {
        const date = new Date(dateString);
        const hours = (Date.now() - date) / 3600000;
        let difference = Math.ceil(hours);
        let display;
        if (difference > 24) {
            display = date.toLocaleDateString();
        } else {
            if (hours < 1) {
                display = 'Less than an hour';
            } else {
                display = `${difference} hour${difference == 1 ? '': 's'}`;
            }
        }
        return display;
    }
}